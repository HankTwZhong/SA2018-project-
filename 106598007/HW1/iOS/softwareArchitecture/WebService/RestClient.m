//
//  RestClient.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/3.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "RestClient.h"
#import <Bolts/BFTask.h>
#import <Bolts/BFTaskCompletionSource.h>

@interface RestClient ()

@property (nonatomic, nullable) NSURL *url;

@end

@implementation RestClient

- (instancetype)initWithNSURL:(NSURL *)url {
    self = [super init];
    if (self) {
        _url = url;
    }
    return self;
}

#pragma mark - pulic implementation

- (BFTask *)response {
    BFTaskCompletionSource *const taskCompletionSource = [BFTaskCompletionSource taskCompletionSource];
    NSURLSession *session = [NSURLSession sharedSession];
    NSURLRequest *request = [NSURLRequest requestWithURL:self.url];
    NSURLSessionDataTask * dataTask =  [session dataTaskWithRequest:request completionHandler:^(NSData * __nullable data, NSURLResponse * __nullable response, NSError * __nullable error) {
        if (data.length > 0 && !error) {
            [taskCompletionSource trySetResult:[NSJSONSerialization JSONObjectWithData:data options:0 error:NULL]];
        } else {
            [taskCompletionSource trySetError:[NSError errorWithDomain:@"software-Architecture" code:500 userInfo:[@"task failed" mutableCopy]]];
        }
    }];
    [dataTask resume];
    return taskCompletionSource.task;
}

@end
