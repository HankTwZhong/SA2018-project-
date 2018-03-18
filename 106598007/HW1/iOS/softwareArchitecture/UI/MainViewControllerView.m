//
//  MainViewControllerView.m
//  softwareArchitecture
//
//  Created by willieho on 2018/3/1.
//  Copyright © 2018年 willieho. All rights reserved.
//

#import "MainViewControllerView.h"
#import <ChameleonFramework/Chameleon.h>
#import "RestClient.h"
#import <Bolts/BFTask.h>

@interface MainViewControllerView ()<UITextFieldDelegate>

@property (weak, nonatomic) IBOutlet UITextField *hostIpTextField;
@property (weak, nonatomic) IBOutlet UIButton *createMonitorButton;

@end

@implementation MainViewControllerView

- (void)awakeFromNib {
    [super awakeFromNib];
    [self setupInstance];
    self.hostIpTextField.delegate = self;
}

#pragma mark - private implementation

- (void)setupInstance {
    self.backgroundColor = [UIColor colorWithHexString:@"c4dff6"];
    self.hostIpTextField.delegate = self;
    self.hostIpTextField.placeholder = @"Host IP";
}

- (void)endEditing {
    
}

#pragma mark - Actions

- (IBAction)createMonitorButtonTouchUpInside:(id)sender {
    if ([self.hostIpTextField isFirstResponder]) {
        [self.hostIpTextField resignFirstResponder];
    }
    if (self.hostIpTextField.text.length && [self.delegate respondsToSelector:@selector(mainViewControllerView:didTapCreateMonitorButtonWithHostIp:)]) {
        [self.delegate mainViewControllerView:self didTapCreateMonitorButtonWithHostIp:self.hostIpTextField.text];
    }
    if (self.hostIpTextField.text.length) {
        self.hostIpTextField.text = @"";
    }
}

#pragma mark - UITextFieldDelegate

- (void)textFieldDidBeginEditing:(UITextField *)textField {
    
}

- (void)textFieldDidEndEditing:(UITextField *)textField {
    [self endEditing];
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    [self.hostIpTextField resignFirstResponder];
    return YES;
}

@end
