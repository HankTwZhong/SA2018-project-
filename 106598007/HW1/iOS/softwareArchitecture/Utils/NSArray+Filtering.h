//
//  NSArray+Filtering.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/6.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

typedef BOOL(^PredicateBlock)(_Nonnull id);
typedef _Nullable id(^Transformer)(_Nonnull id);

@interface NSArray<ObjectType> (Filtering)

- (nullable ObjectType)findUsingCondition:(BOOL (^)(ObjectType obj))condition;

- (NSArray<ObjectType> *)filteredArrayUsingCondition:(BOOL (^)(ObjectType obj))condition;

- (NSArray<ObjectType> *)filteredArrayUsingConditionWithIndex:(BOOL (^)(ObjectType obj, NSUInteger idx))condition;

- (NSArray *)flatMapWithBlock:(NSArray *(^)(id))block;

- (NSArray *)mapObjectsWithBlock:(id (^)(ObjectType obj, NSUInteger idx))block;

- (id)reduceWithBlock:(id (^)(id accumulator, ObjectType obj, NSUInteger idx))block start:(id)start;

- (NSDictionary *)toDictionary:(Transformer)transformer;

- (NSInteger)indexOfObjectWithBlock:(PredicateBlock)predicate;

- (NSInteger)indexOfObjectWithPredicate:(NSPredicate *)predicate;

@end

NS_ASSUME_NONNULL_END
