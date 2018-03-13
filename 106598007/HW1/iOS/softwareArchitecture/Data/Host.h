//
//  Host.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface Host : NSObject

@property (nonatomic) NSString *name;
@property (nonatomic) NSString *hostIp;
@property (nonatomic) NSString *status;
@property (nonatomic) NSDate *updateTime;

- (instancetype)initWithName:(NSString *)name hostIp:(NSString *)hostIp status:(NSString *)status updateTime:(NSDate *)updateTime;

@end
