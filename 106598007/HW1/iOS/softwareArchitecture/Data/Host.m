//
//  Host.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "Host.h"

@implementation Host

- (instancetype)initWithName:(NSString *)name hostIp:(NSString *)hostIp status:(NSString *)status updateTime:(NSDate *)updateTime {
    self = [super init];
    if (self) {
        _name = name;
        _hostIp = hostIp;
        _status = status;
        _updateTime = updateTime;
    }
    return self;
}

@end
