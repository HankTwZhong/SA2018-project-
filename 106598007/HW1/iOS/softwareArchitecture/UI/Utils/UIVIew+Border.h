//
//  UIVIew+Border.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/4.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface UIView (Border)

@property (nonatomic) IBInspectable CGFloat borderWidth;
@property (nonatomic) IBInspectable UIColor *borderColor;
@property (nonatomic) IBInspectable CGFloat cornerRadius;

@end
