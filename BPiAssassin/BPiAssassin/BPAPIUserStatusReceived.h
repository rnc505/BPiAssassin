//
//  BPUserStatusReceived.h
//  BPiAssassin
//
//  Created by Robby Cohen on 4/7/14.
//  Copyright (c) 2014 BP. All rights reserved.
//

#import "BPAPIClientObject.h"

@interface BPAPIUserStatusReceived : BPAPIClientObject

@property(nonatomic, copy) NSString* status;
@property(nonatomic, copy) NSString* gameId;
-(id)initWithDict:(NSDictionary *)dict;
@end
