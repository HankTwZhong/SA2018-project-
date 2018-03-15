//
//  RestClient.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <Foundation/Foundation.h>

@class BFTask;

@interface RestClient : NSObject

- (instancetype)initWithNSURL:(NSURL *)url;

- (BFTask *)response;

@end
