//
//  NSArray+Filtering.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/6.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "NSArray+Filtering.h"

@implementation NSArray (Filtering)

- (id)findUsingCondition:(BOOL (^)(id obj))condition; {
    for (id item in self) {
        if (condition(item)) {
            return item;
        }
    }
    return nil;
}

- (NSArray *)filteredArrayUsingCondition:(BOOL (^)(id obj))condition; {
    return [self filteredArrayUsingConditionWithIndex:^BOOL(id obj, NSUInteger idx) {
        return condition(obj);
    }];
}

- (NSArray *)filteredArrayUsingConditionWithIndex:(BOOL (^)(id obj, NSUInteger idx))condition {
    NSMutableArray *const filteredArray = [[NSMutableArray alloc] init];
    [self enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        if (condition(obj, idx)) {
            [filteredArray addObject:obj];
        }
    }];
    return filteredArray;
}

- (NSArray *)flatMapWithBlock:(NSArray *(^)(id))block {
    NSMutableArray *result = [NSMutableArray array];
    [self enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        NSArray *flattedResults = block(obj);
        if (flattedResults) {
            [result addObjectsFromArray:flattedResults];
        }
    }];
    return result;
}

- (NSArray *)mapObjectsWithBlock:(id (^)(id obj, NSUInteger idx))block {
    NSMutableArray *result = [NSMutableArray arrayWithCapacity:[self count]];
    [self enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        id mappedObj = block(obj, idx);
        if (mappedObj) {
            [result addObject:mappedObj];
        }
    }];
    return result;
}

- (id)reduceWithBlock:(id (^)(id, id, NSUInteger))block start:(id)start {
    __block id accumulator = start;
    [self enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        accumulator = block(accumulator, obj, idx);
    }];
    return accumulator;
}

- (NSDictionary *)toDictionary:(Transformer)transformer {
    NSMutableDictionary *result = [NSMutableDictionary new];
    for (id element in self) {
        id transformed = transformer(element);
        if (transformed) {
            [result setObject:transformed forKey:element];
        }
    }
    return result;
}

- (NSInteger)indexOfObjectWithBlock:(PredicateBlock)predicate {
    __block NSInteger index = NSNotFound;
    [self enumerateObjectsUsingBlock:^(id _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        if (predicate(obj)) {
            index = idx;
            *stop = YES;
        }
    }];
    return index;
}

- (NSInteger)indexOfObjectWithPredicate:(NSPredicate *)predicate {
    return [self indexOfObjectWithBlock:^BOOL(id _Nonnull item) {
        return [predicate evaluateWithObject:item];
    }];
}

@end
