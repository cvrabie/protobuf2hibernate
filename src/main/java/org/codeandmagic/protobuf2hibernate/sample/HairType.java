// Generated by the protocol buffer compiler.  DO NOT EDIT!

package org.codeandmagic.protobuf2hibernate.sample;

public enum HairType
    implements com.google.protobuf.ProtocolMessageEnum {
  STRAIGHT(0, 0),
  CURLY(1, 1),
  ;
  
  
  public final int getNumber() { return value; }
  
  public static HairType valueOf(int value) {
    switch (value) {
      case 0: return STRAIGHT;
      case 1: return CURLY;
      default: return null;
    }
  }
  
  public static com.google.protobuf.Internal.EnumLiteMap<HairType>
      internalGetValueMap() {
    return internalValueMap;
  }
  private static com.google.protobuf.Internal.EnumLiteMap<HairType>
      internalValueMap =
        new com.google.protobuf.Internal.EnumLiteMap<HairType>() {
          public HairType findValueByNumber(int number) {
            return HairType.valueOf(number)
  ;        }
        };
  
  public final com.google.protobuf.Descriptors.EnumValueDescriptor
      getValueDescriptor() {
    return getDescriptor().getValues().get(index);
  }
  public final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptorForType() {
    return getDescriptor();
  }
  public static final com.google.protobuf.Descriptors.EnumDescriptor
      getDescriptor() {
    return org.codeandmagic.protobuf2hibernate.sample.Descriptor_Cat.getDescriptor().getEnumTypes().get(0);
  }
  
  private static final HairType[] VALUES = {
    STRAIGHT, CURLY, 
  };
  public static HairType valueOf(
      com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
    if (desc.getType() != getDescriptor()) {
      throw new java.lang.IllegalArgumentException(
        "EnumValueDescriptor is not for this type.");
    }
    return VALUES[desc.getIndex()];
  }
  private final int index;
  private final int value;
  private HairType(int index, int value) {
    this.index = index;
    this.value = value;
  }
  
  static {
    org.codeandmagic.protobuf2hibernate.sample.Descriptor_Cat.getDescriptor();
  }
}

