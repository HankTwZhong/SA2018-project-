//
//  MainViewController.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/1.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "MainViewController.h"
#import "MainViewControllerView.h"
#import "HostInfoViewController.h"
#import <PureLayout/PureLayout.h>

@interface MainViewController () <MainViewControllerViewDelegate>

@property (nonatomic) HostInfoViewController *hostInfoViewController;

@end

@implementation MainViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    [self setupUI];
    [self showHostInfoViewController];
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
}

#pragma mark - MainViewControllerViewDelegate

- (void)mainViewControllerView:(MainViewControllerView *)mainView didTapCreateMonitorButtonWithHostIp:(NSString *)ip {
    [self.hostInfoViewController createMonitorWithIp:ip];
}

#pragma mark - private implementation

- (void)setupUI {
    MainViewControllerView *mainViewControllerView = [[NSBundle mainBundle] loadNibNamed:@"MainViewControllerView" owner:self options:nil][0];
    mainViewControllerView.delegate = self;
    [self.view addSubview:mainViewControllerView];
}

- (void)showHostInfoViewController {
    if (self.hostInfoViewController) {
        [self dismissHostInfoViewController];
    }
    self.hostInfoViewController = [[HostInfoViewController alloc] init];
    [self addChildViewController:self.hostInfoViewController];
    [self.view addSubview:self.hostInfoViewController.view];
    [self.hostInfoViewController.view autoPinEdgesToSuperviewEdgesWithInsets:(UIEdgeInsets) {
        .top = 150.f
    }];
    [self.hostInfoViewController didMoveToParentViewController:self];
}

- (void)dismissHostInfoViewController {
    [self.hostInfoViewController willMoveToParentViewController:nil];
    [self.hostInfoViewController.view removeFromSuperview];
    [self.hostInfoViewController removeFromParentViewController];
    self.hostInfoViewController = nil;
}

@end
