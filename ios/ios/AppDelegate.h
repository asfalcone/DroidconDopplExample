//
//  AppDelegate.h
//  ios
//
//  Created by Kevin Galligan on 2/23/16.
//  Copyright © 2016 Kevin Galligan. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "co/touchlab/droidconandroid/presenter/AppManager.h"
#import "co/touchlab/droidconandroid/ios/IosFirebase.h"

@interface AppDelegate : UIResponder <UIApplicationDelegate, DCPAppManager_LoadDataSeed, DCIosFirebase>

@property (strong, nonatomic) UIWindow *window;


@end

