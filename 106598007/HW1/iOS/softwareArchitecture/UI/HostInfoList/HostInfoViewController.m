//
//  HostInfoViewController.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "HostInfoViewController.h"
#import "HostInfoTableViewCell.h"
#import "HostInfoTableHeaderView.h"
#import <ChameleonFramework/Chameleon.h>
#import "RestClient.h"
#import <Bolts/BFTask.h>
#import "Host.h"
#import "NSArray+Filtering.h"
#include <arpa/inet.h>

@interface HostInfoViewController () <UITableViewDelegate, UITableViewDataSource>

@property (weak, nonatomic) IBOutlet UITableView *hostInfoTableView;
@property (nonatomic) NSArray <Host *> *hosts;
@property (nonatomic, nullable) NSTimer *hostRefreshTimer;
@property (nonatomic, nullable) NSMutableArray <NSString *> *queryStrings;

@end

@implementation HostInfoViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setupUI];
}

- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];
    _queryStrings = [NSMutableArray array];
    self.hostRefreshTimer = [NSTimer scheduledTimerWithTimeInterval:10.0f target:self selector:@selector(hostRefreshEvent:) userInfo:nil repeats:YES];
}

- (void)viewWillDisappear:(BOOL)animated {
    [super viewWillDisappear:animated];
    [self.hostRefreshTimer invalidate];
    self.hostRefreshTimer = nil;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

#pragma mark - public implementation

- (void)createMonitorWithIp:(NSString *)ip {
    [self.queryStrings addObject:ip];
    RestClient *client = [[RestClient alloc] initWithNSURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://hang-server.synology.me:3001/api/%@", ip]]];
    [[client response] continueWithBlock:^id _Nullable(BFTask * _Nonnull task) {
        if (task.error) {
            [self showRequestFailedAlert];
        } else if (task.result) {
            NSDictionary *response = task.result;
            NSDictionary *hostJson = [[response valueForKey:@"data"] valueForKey:@"scanResults"];
            NSString *hostName;
            NSString *hostIp;
            NSString *status;
            if (response && hostJson.count != 0) {
                hostName = [hostJson valueForKey:@"hostname"][0] == [NSNull null] ? @"nil" : [hostJson valueForKey:@"hostname"][0];
                hostIp = [hostJson valueForKey:@"ip"][0] == [NSNull null] ? @"nil" : [hostJson valueForKey:@"ip"][0];
                status = @"Up";
            } else {
                NSLog(@"response nil");
                hostName = [self isValidIpAddress:ip] ? @"nil" : ip;
                hostIp = [self isValidIpAddress:ip] ? ip : @"nil";
                status = @"Down";
            }
            NSDate *updateTime = [NSDate date];
            Host *host = [[Host alloc] initWithName:hostName hostIp:hostIp status:status updateTime:updateTime];
            [self generateSectionWithHost:host];
        }
        return nil;
    }];
}

#pragma mark - UITableViewDelegate

- (CGFloat)tableView:(UITableView *)tableView heightForHeaderInSection:(NSInteger)section {
    return 44.f;
}

- (UIView *)tableView:(UITableView *)tableView viewForHeaderInSection:(NSInteger)section {
    HostInfoTableHeaderView *headerView = [tableView dequeueReusableHeaderFooterViewWithIdentifier:HostInfoTableHeaderViewReuseIdentifier];
    headerView.contentView.backgroundColor = [UIColor colorWithHexString:@"D9D9D9"];
    return headerView;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    // romove host
    Host *removeHost = self.hosts[indexPath.row];
    [self.queryStrings removeObject:[self queryStringFromHost:removeHost]];
    NSMutableArray <Host *> *hosts = [self.hosts mutableCopy];
    [hosts removeObject:removeHost];
    self.hosts = [hosts copy];
    [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationRight];
    [tableView reloadData];
}

#pragma mark - UITableViewDatasource

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.hosts.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    HostInfoTableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:HostInfoTableViewCellReuseIdentifier forIndexPath:indexPath];
    cell.host = self.hosts[indexPath.row];
    return cell;
}

#pragma mark - private implementation

- (void)setupUI {
    UIView *hostInfoView = [[NSBundle mainBundle] loadNibNamed:@"HostInfoView" owner:self options:nil][0];
    self.view = hostInfoView;
    
    [self.hostInfoTableView registerNib:[UINib nibWithNibName:@"HostInfoTableViewCell" bundle:nil] forCellReuseIdentifier:HostInfoTableViewCellReuseIdentifier];
    [self.hostInfoTableView registerNib:[UINib nibWithNibName:@"HostInfoTableHeaderView" bundle:nil] forHeaderFooterViewReuseIdentifier:HostInfoTableHeaderViewReuseIdentifier];
    self.hostInfoTableView.delegate = self;
    self.hostInfoTableView.dataSource = self;
    self.hostInfoTableView.allowsSelection = NO;
}

- (void)generateSectionWithHost:(Host *)host {
    NSMutableArray *hostsArray = [NSMutableArray array];
    [hostsArray addObjectsFromArray:self.hosts];
    [hostsArray addObject:host];
    self.hosts = [hostsArray copy];
    dispatch_async(dispatch_get_main_queue(), ^{
        [self.hostInfoTableView reloadSections:[NSIndexSet indexSetWithIndex:0] withRowAnimation:UITableViewRowAnimationFade];
    });
}

- (void)hostRefreshEvent:(NSTimer *)timer {
    [self reload];
}

- (void)reload {
    NSArray <NSString *> *queryStrings = [self.queryStrings copy];
    [queryStrings enumerateObjectsUsingBlock:^(NSString *queryString, NSUInteger idx, BOOL * _Nonnull stop) {
        RestClient *client = [[RestClient alloc] initWithNSURL:[NSURL URLWithString:[NSString stringWithFormat:@"http://hang-server.synology.me:3001/api/%@", queryString]]];
        [[client response] continueWithBlock:^id _Nullable(BFTask * _Nonnull task) {
            if (task.result) {
                NSDictionary *response = task.result;
                NSDictionary *hostJson = [[response valueForKey:@"data"] valueForKey:@"scanResults"];
                Host *host = [self hostWithQueryString:queryString];
                host.status = response && hostJson.count != 0 ? @"Up" : @"Down";
                host.updateTime = [NSDate date];
                dispatch_async(dispatch_get_main_queue(), ^{
                    [self.hostInfoTableView reloadData];
                });
            }
            return nil;
        }];
    }];
}

- (Host *)hostWithQueryString:(NSString *)queryString {
    return [self.hosts filteredArrayUsingCondition:^BOOL(Host *host) {
        return [host.hostIp isEqualToString:queryString] || [host.name isEqualToString:queryString];
    }].firstObject;
}

- (NSString *)queryStringFromHost:(Host *)host {
    NSArray <NSString *> *queryStrings = [self.queryStrings copy];
    return [queryStrings filteredArrayUsingCondition:^BOOL(NSString *obj) {
        return [obj isEqualToString:host.name] || [obj isEqualToString:host.hostIp];
    }].firstObject;
}

- (void)showRequestFailedAlert {
    UIAlertController *alert = [UIAlertController alertControllerWithTitle:nil message:@"Request failed" preferredStyle:UIAlertControllerStyleAlert];
    UIAlertAction *okAction = [UIAlertAction actionWithTitle:@"Ok" style:UIAlertActionStyleDefault handler:nil];
    [alert addAction:okAction];
    [self presentViewController:alert animated:YES completion:nil];
}

- (BOOL)isValidIpAddress:(NSString *)ip {
    NSArray *parts = [ip componentsSeparatedByString:@"."];
    NSArray <NSNumber *> *nums = [[parts filteredArrayUsingCondition:^BOOL(id  _Nonnull obj) {
        NSScanner *scanner = [NSScanner scannerWithString:obj];
        return [scanner scanInteger:NULL] && [scanner isAtEnd];
    }] mapObjectsWithBlock:^id _Nonnull(id obj, NSUInteger idx) {
        return @([obj integerValue]);
    }];
    return parts.count == 4 && nums.count == 4 && [nums filteredArrayUsingCondition:^BOOL(NSNumber *obj) {
        return obj.integerValue >= 0 && obj.integerValue < 255;
    }].count == 4;
}

@end
