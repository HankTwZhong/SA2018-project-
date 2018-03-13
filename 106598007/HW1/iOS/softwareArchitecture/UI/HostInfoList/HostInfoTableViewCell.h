//
//  HostInfoTableViewCell.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <UIKit/UIKit.h>

@class Host;

extern NSString *const HostInfoTableViewCellReuseIdentifier;

@interface HostInfoTableViewCell : UITableViewCell

@property (nonatomic) Host *host;

@end
