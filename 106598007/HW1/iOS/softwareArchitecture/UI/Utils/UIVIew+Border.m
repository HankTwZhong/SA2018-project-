//
//  UIVIew+Border.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/4.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "UIView+Border.h"

@implementation UIView (Border)

- (void)setBorderColor:(UIColor *)borderColor {
    self.layer.borderColor = borderColor.CGColor;
}

- (UIColor *)borderColor {
    return [UIColor colorWithCGColor:self.layer.borderColor];
}

- (void)setBorderWidth:(CGFloat)borderWidth {
    self.layer.borderWidth = borderWidth;
}

- (CGFloat)borderWidth {
    return self.layer.borderWidth;
}

- (void)setCornerRadius:(CGFloat)cornerRadius {
    self.layer.cornerRadius = cornerRadius;
    self.layer.masksToBounds = cornerRadius > 0.f;
}

- (CGFloat)cornerRadius {
    return self.layer.cornerRadius;
}

@end
