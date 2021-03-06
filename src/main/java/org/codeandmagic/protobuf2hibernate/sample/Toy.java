// Generated by the protocol buffer compiler.  DO NOT EDIT!

package org.codeandmagic.protobuf2hibernate.sample;

public  final class Toy extends
    com.google.protobuf.GeneratedMessage {
  // Use Toy.newBuilder() to construct.
  private Toy() {}
  
  private static final Toy defaultInstance = new Toy();
  public static Toy getDefaultInstance() {
    return defaultInstance;
  }
  
  public Toy getDefaultInstanceForType() {
    return defaultInstance;
  }
  
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.codeandmagic.protobuf2hibernate.sample.Descriptor_Toy.internal_static_domain_Toy_descriptor;
  }
  
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.codeandmagic.protobuf2hibernate.sample.Descriptor_Toy.internal_static_domain_Toy_fieldAccessorTable;
  }
  
  // required string name = 3;
  public static final int NAME_FIELD_NUMBER = 3;
  private boolean hasName;
  private java.lang.String name_ = "";
  public boolean hasName() { return hasName; }
  public java.lang.String getName() { return name_; }
  
  // optional string description = 4;
  public static final int DESCRIPTION_FIELD_NUMBER = 4;
  private boolean hasDescription;
  private java.lang.String description_ = "";
  public boolean hasDescription() { return hasDescription; }
  public java.lang.String getDescription() { return description_; }
  
  public boolean equals(Object otherObject) {
    if (otherObject == this) return true;
    if (!(otherObject instanceof Toy)) return false;
    if (hashCode() != otherObject.hashCode()) return false;
    Toy other = (Toy) otherObject;
    if (hasName) {
      if (!other.hasName) return false;
      if (!name_.equals(other.name_)) return false;
    } else {
      if (other.hasName) return false;
    }
    if (hasDescription) {
      if (!other.hasDescription) return false;
      if (!description_.equals(other.description_)) return false;
    } else {
      if (other.hasDescription) return false;
    }
    return getUnknownFields().equals(other.getUnknownFields());
  }
  public boolean super_equals(final Object otherObject) { return super.equals(otherObject); }
  
  private int computedHashCode = 0;
  public int hashCode() {
    if (computedHashCode == 0) {
      computedHashCode = super.hashCode();
    }
    return computedHashCode;
  }
  public int super_hashCode() { return super.hashCode(); }
  
  public final boolean isInitialized() {
    if (!hasName) return false;
    return true;
  }
  
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (hasName()) {
      output.writeString(3, getName());
    }
    if (hasDescription()) {
      output.writeString(4, getDescription());
    }
    getUnknownFields().writeTo(output);
  }
  
  private int memoizedSerializedSize = -1;
  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;
  
    size = 0;
    if (hasName()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(3, getName());
    }
    if (hasDescription()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(4, getDescription());
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSerializedSize = size;
    return size;
  }
  
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return newBuilder().mergeDelimitedFrom(input).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Toy parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input, extensionRegistry)
             .buildParsed();
  }
  
  public static Builder newBuilder() { return Builder.create(); }
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder(org.codeandmagic.protobuf2hibernate.sample.Toy prototype) {
    return newBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() { return newBuilder(this); }
  
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> {
    private org.codeandmagic.protobuf2hibernate.sample.Toy result;
    
    // Construct using org.codeandmagic.protobuf2hibernate.sample.Toy.newBuilder()
    private Builder() {}
    
    private static Builder create() {
      Builder builder = new Builder();
      builder.result = new org.codeandmagic.protobuf2hibernate.sample.Toy();
      return builder;
    }
    
    protected org.codeandmagic.protobuf2hibernate.sample.Toy internalGetResult() {
      return result;
    }
    
    public Builder clear() {
      if (result == null) {
        throw new IllegalStateException(
          "Cannot call clear() after build().");
      }
      result = new org.codeandmagic.protobuf2hibernate.sample.Toy();
      return this;
    }
    
    public Builder clone() {
      return create().mergeFrom(result);
    }
    
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.codeandmagic.protobuf2hibernate.sample.Toy.getDescriptor();
    }
    
    public org.codeandmagic.protobuf2hibernate.sample.Toy getDefaultInstanceForType() {
      return org.codeandmagic.protobuf2hibernate.sample.Toy.getDefaultInstance();
    }
    
    public boolean isInitialized() {
      return result.isInitialized();
    }
    public org.codeandmagic.protobuf2hibernate.sample.Toy build() {
      if (result != null && !isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return buildPartial();
    }
    
    private org.codeandmagic.protobuf2hibernate.sample.Toy buildParsed()
        throws com.google.protobuf.InvalidProtocolBufferException {
      if (!isInitialized()) {
        throw newUninitializedMessageException(
          result).asInvalidProtocolBufferException();
      }
      return buildPartial();
    }
    
    public org.codeandmagic.protobuf2hibernate.sample.Toy buildPartial() {
      if (result == null) {
        throw new IllegalStateException(
          "build() has already been called on this Builder.");
      }
      org.codeandmagic.protobuf2hibernate.sample.Toy returnMe = result;
      result = null;
      return returnMe;
    }
    
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.codeandmagic.protobuf2hibernate.sample.Toy) {
        return mergeFrom((org.codeandmagic.protobuf2hibernate.sample.Toy)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }
    
    public Builder mergeFrom(org.codeandmagic.protobuf2hibernate.sample.Toy other) {
      if (other == org.codeandmagic.protobuf2hibernate.sample.Toy.getDefaultInstance()) return this;
      if (other.hasName()) {
        setName(other.getName());
      }
      if (other.hasDescription()) {
        setDescription(other.getDescription());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      return this;
    }
    
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder(
          this.getUnknownFields());
      while (true) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            this.setUnknownFields(unknownFields.build());
            return this;
          default: {
            if (!parseUnknownField(input, unknownFields,
                                   extensionRegistry, tag)) {
              this.setUnknownFields(unknownFields.build());
              return this;
            }
            break;
          }
          case 26: {
            setName(input.readString());
            break;
          }
          case 34: {
            setDescription(input.readString());
            break;
          }
        }
      }
    }
    
    
    // required string name = 3;
    public boolean hasName() {
      return result.hasName();
    }
    public java.lang.String getName() {
      return result.getName();
    }
    public Builder setName(java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  result.hasName = true;
      result.name_ = value;
      return this;
    }
    public Builder clearName() {
      result.hasName = false;
      result.name_ = getDefaultInstance().getName();
      return this;
    }
    
    // optional string description = 4;
    public boolean hasDescription() {
      return result.hasDescription();
    }
    public java.lang.String getDescription() {
      return result.getDescription();
    }
    public Builder setDescription(java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  result.hasDescription = true;
      result.description_ = value;
      return this;
    }
    public Builder clearDescription() {
      result.hasDescription = false;
      result.description_ = getDefaultInstance().getDescription();
      return this;
    }
  }
  
  static {
    org.codeandmagic.protobuf2hibernate.sample.Descriptor_Toy.getDescriptor();
  }
  
  static {
    org.codeandmagic.protobuf2hibernate.sample.Descriptor_Toy.internalForceInit();
  }
}

