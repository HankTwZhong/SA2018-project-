//
//  NSDate+FormatString.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface NSDate (FormatString)
- (NSString *)serialTimeString;

- (NSString *)simpleTimeString;

- (NSString *)fullTimeString;

- (NSString *)completeTimeString;

- (NSString *)simpleDateString;

- (NSString *)weekDateString;

@end

NS_ASSUME_NONNULL_END
