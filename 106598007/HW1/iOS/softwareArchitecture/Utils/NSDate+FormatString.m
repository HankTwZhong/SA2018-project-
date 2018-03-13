//
//  NSDate+FormatString.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "NSDate+FormatString.h"
#import "NSDateFormatter+Helper.h"

@implementation NSDate (FormatString)

- (NSString *)serialTimeString {
    return [[NSDateFormatter serialTimeDateFormatter] stringFromDate:self];
}

- (NSString *)simpleTimeString {
    return [[NSDateFormatter simpleTimeDateFormatter] stringFromDate:self];
}

- (NSString *)completeTimeString {
    return [[NSDateFormatter completeTimeDateFormatter] stringFromDate:self];
}

- (NSString *)fullTimeString {
    return [[NSDateFormatter fullTimeDateFormatter] stringFromDate:self];
}

- (NSString *)simpleDateString {
    return [[NSDateFormatter simpleDateDateFormatter] stringFromDate:self];
}

- (NSString *)weekDateString {
    return [[NSDateFormatter weekDateDateFormatter] stringFromDate:self];
}

@end
