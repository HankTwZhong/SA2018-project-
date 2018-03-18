//
//  NSDateFormatter+Helper.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface NSDateFormatter (Helper)

+ (NSDateFormatter *)serialTimeDateFormatter;
+ (NSDateFormatter *)simpleTimeDateFormatter;
+ (NSDateFormatter *)completeTimeDateFormatter;
+ (NSDateFormatter *)fullTimeDateFormatter;
+ (NSDateFormatter *)simpleDateDateFormatter;
+ (NSDateFormatter *)weekDateDateFormatter;
+ (NSDateFormatter *)dayFormatter;
+ (NSDateFormatter *)firstDayFormatter;
+ (NSDateFormatter *)monthFormatter;
+ (NSDateFormatter *)yearFormatter;
+ (NSDateFormatter *)yearMonthFormatter;

@end

NS_ASSUME_NONNULL_END
