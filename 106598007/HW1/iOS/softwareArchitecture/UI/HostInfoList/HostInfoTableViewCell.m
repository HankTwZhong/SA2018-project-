//
//  HostInfoTableViewCell.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "HostInfoTableViewCell.h"
#import "Host.h"
#import "NSDate+FormatString.h"
#import <ChameleonFramework/Chameleon.h>

NSString *const HostInfoTableViewCellReuseIdentifier = @"hostInfoTableViewCellReuseIdentifier";

@interface HostInfoTableViewCell ()

@property (weak, nonatomic) IBOutlet UILabel *hostNameLabel;
@property (weak, nonatomic) IBOutlet UILabel *ipLabel;
@property (weak, nonatomic) IBOutlet UILabel *hostStatusLabel;
@property (weak, nonatomic) IBOutlet UILabel *updateTimeLabel;


@end

@implementation HostInfoTableViewCell

- (void)awakeFromNib {
    [super awakeFromNib];
}

- (void)setHost:(Host *)host {
    self.hostNameLabel.text = host.name;
    self.ipLabel.text = host.hostIp;
    self.hostStatusLabel.text = host.status;
    self.hostStatusLabel.textColor = [host.status isEqualToString:@"Up"] ? [UIColor colorWithHexString:@"50D979"] : [UIColor colorWithHexString:@"D0021B"];
    self.updateTimeLabel.text = [host.updateTime completeTimeString];
}

@end
