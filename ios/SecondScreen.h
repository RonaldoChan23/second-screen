
#ifdef RCT_NEW_ARCH_ENABLED
#import "RNSecondScreenSpec.h"

@interface SecondScreen : NSObject <NativeSecondScreenSpec>
#else
#import <React/RCTBridgeModule.h>

@interface SecondScreen : NSObject <RCTBridgeModule>
#endif

@end
