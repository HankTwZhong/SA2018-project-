//
//  MainViewControllerView.h
//  softwareArchitecture
//
//  Created by willieho on 2018/3/1.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import <UIKit/UIKit.h>

@class MainViewControllerView;

@protocol MainViewControllerViewDelegate <NSObject>

- (void)mainViewControllerView:(MainViewControllerView *)mainView didTapCreateMonitorButtonWithHostIp:(NSString *)ip;

@end

@interface MainViewControllerView : UIView

@property (nonatomic, weak) id<MainViewControllerViewDelegate> delegate;

@end
