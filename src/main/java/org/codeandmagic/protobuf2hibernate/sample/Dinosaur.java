// Generated by the protocol buffer compiler.  DO NOT EDIT!

package org.codeandmagic.protobuf2hibernate.sample;

public  final class Dinosaur extends
    com.google.protobuf.GeneratedMessage {
  // Use Dinosaur.newBuilder() to construct.
  private Dinosaur() {}
  
  private static final Dinosaur defaultInstance = new Dinosaur();
  public static Dinosaur getDefaultInstance() {
    return defaultInstance;
  }
  
  public Dinosaur getDefaultInstanceForType() {
    return defaultInstance;
  }
  
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return org.codeandmagic.protobuf2hibernate.sample.Descriptor_Dinosaur.internal_static_domain_Dinosaur_descriptor;
  }
  
  protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return org.codeandmagic.protobuf2hibernate.sample.Descriptor_Dinosaur.internal_static_domain_Dinosaur_fieldAccessorTable;
  }
  
  // required string uuid = 1;
  public static final int UUID_FIELD_NUMBER = 1;
  private boolean hasUuid;
  private java.lang.String uuid_ = "";
  public boolean hasUuid() { return hasUuid; }
  public java.lang.String getUuid() { return uuid_; }
  
  // required int64 age = 2;
  public static final int AGE_FIELD_NUMBER = 2;
  private boolean hasAge;
  private long age_ = 0L;
  public boolean hasAge() { return hasAge; }
  public long getAge() { return age_; }
  
  // required string name = 3;
  public static final int NAME_FIELD_NUMBER = 3;
  private boolean hasName;
  private java.lang.String name_ = "";
  public boolean hasName() { return hasName; }
  public java.lang.String getName() { return name_; }
  
  // optional float tailLength = 4;
  public static final int TAILLENGTH_FIELD_NUMBER = 4;
  private boolean hasTailLength;
  private float tailLength_ = 0F;
  public boolean hasTailLength() { return hasTailLength; }
  public float getTailLength() { return tailLength_; }
  
  // repeated string victims = 5;
  public static final int VICTIMS_FIELD_NUMBER = 5;
  private java.util.List<java.lang.String> victims_ =
    java.util.Collections.emptyList();
  public java.util.List<java.lang.String> getVictimsList() {
    return victims_;
  }
  public int getVictimsCount() { return victims_.size(); }
  public java.lang.String getVictims(int index) {
    return victims_.get(index);
  }
  
  public boolean equals(Object otherObject) {
    if (otherObject == this) return true;
    if (!(otherObject instanceof Dinosaur)) return false;
    if (hashCode() != otherObject.hashCode()) return false;
    Dinosaur other = (Dinosaur) otherObject;
    if (hasUuid) {
      if (!other.hasUuid) return false;
      if (!uuid_.equals(other.uuid_)) return false;
    } else {
      if (other.hasUuid) return false;
    }
    if (hasAge) {
      if (!other.hasAge) return false;
      if (age_ != other.age_) return false;
    } else {
      if (other.hasAge) return false;
    }
    if (hasName) {
      if (!other.hasName) return false;
      if (!name_.equals(other.name_)) return false;
    } else {
      if (other.hasName) return false;
    }
    if (hasTailLength) {
      if (!other.hasTailLength) return false;
      if (Float.floatToIntBits(tailLength_) != Float.floatToIntBits(other.tailLength_)) return false;
    } else {
      if (other.hasTailLength) return false;
    }
    if (!victims_.equals(other.victims_)) return false;
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
    if (!hasUuid) return false;
    if (!hasAge) return false;
    if (!hasName) return false;
    return true;
  }
  
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (hasUuid()) {
      output.writeString(1, getUuid());
    }
    if (hasAge()) {
      output.writeInt64(2, getAge());
    }
    if (hasName()) {
      output.writeString(3, getName());
    }
    if (hasTailLength()) {
      output.writeFloat(4, getTailLength());
    }
    for (java.lang.String element : getVictimsList()) {
      output.writeString(5, element);
    }
    getUnknownFields().writeTo(output);
  }
  
  private int memoizedSerializedSize = -1;
  public int getSerializedSize() {
    int size = memoizedSerializedSize;
    if (size != -1) return size;
  
    size = 0;
    if (hasUuid()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(1, getUuid());
    }
    if (hasAge()) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt64Size(2, getAge());
    }
    if (hasName()) {
      size += com.google.protobuf.CodedOutputStream
        .computeStringSize(3, getName());
    }
    if (hasTailLength()) {
      size += com.google.protobuf.CodedOutputStream
        .computeFloatSize(4, getTailLength());
    }
    {
      int dataSize = 0;
      for (java.lang.String element : getVictimsList()) {
        dataSize += com.google.protobuf.CodedOutputStream
          .computeStringSizeNoTag(element);
      }
      size += dataSize;
      size += 1 * getVictimsList().size();
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSerializedSize = size;
    return size;
  }
  
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return newBuilder().mergeFrom(data, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return newBuilder().mergeDelimitedFrom(input).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeDelimitedFrom(input, extensionRegistry)
             .buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input).buildParsed();
  }
  public static org.codeandmagic.protobuf2hibernate.sample.Dinosaur parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return newBuilder().mergeFrom(input, extensionRegistry)
             .buildParsed();
  }
  
  public static Builder newBuilder() { return Builder.create(); }
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder(org.codeandmagic.protobuf2hibernate.sample.Dinosaur prototype) {
    return newBuilder().mergeFrom(prototype);
  }
  public Builder toBuilder() { return newBuilder(this); }
  
  public static final class Builder extends
      com.google.protobuf.GeneratedMessage.Builder<Builder> {
    private org.codeandmagic.protobuf2hibernate.sample.Dinosaur result;
    
    // Construct using org.codeandmagic.protobuf2hibernate.sample.Dinosaur.newBuilder()
    private Builder() {}
    
    private static Builder create() {
      Builder builder = new Builder();
      builder.result = new org.codeandmagic.protobuf2hibernate.sample.Dinosaur();
      return builder;
    }
    
    protected org.codeandmagic.protobuf2hibernate.sample.Dinosaur internalGetResult() {
      return result;
    }
    
    public Builder clear() {
      if (result == null) {
        throw new IllegalStateException(
          "Cannot call clear() after build().");
      }
      result = new org.codeandmagic.protobuf2hibernate.sample.Dinosaur();
      return this;
    }
    
    public Builder clone() {
      return create().mergeFrom(result);
    }
    
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return org.codeandmagic.protobuf2hibernate.sample.Dinosaur.getDescriptor();
    }
    
    public org.codeandmagic.protobuf2hibernate.sample.Dinosaur getDefaultInstanceForType() {
      return org.codeandmagic.protobuf2hibernate.sample.Dinosaur.getDefaultInstance();
    }
    
    public boolean isInitialized() {
      return result.isInitialized();
    }
    public org.codeandmagic.protobuf2hibernate.sample.Dinosaur build() {
      if (result != null && !isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return buildPartial();
    }
    
    private org.codeandmagic.protobuf2hibernate.sample.Dinosaur buildParsed()
        throws com.google.protobuf.InvalidProtocolBufferException {
      if (!isInitialized()) {
        throw newUninitializedMessageException(
          result).asInvalidProtocolBufferException();
      }
      return buildPartial();
    }
    
    public org.codeandmagic.protobuf2hibernate.sample.Dinosaur buildPartial() {
      if (result == null) {
        throw new IllegalStateException(
          "build() has already been called on this Builder.");
      }
      if (result.victims_ != java.util.Collections.EMPTY_LIST) {
        result.victims_ =
          java.util.Collections.unmodifiableList(result.victims_);
      }
      org.codeandmagic.protobuf2hibernate.sample.Dinosaur returnMe = result;
      result = null;
      return returnMe;
    }
    
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof org.codeandmagic.protobuf2hibernate.sample.Dinosaur) {
        return mergeFrom((org.codeandmagic.protobuf2hibernate.sample.Dinosaur)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }
    
    public Builder mergeFrom(org.codeandmagic.protobuf2hibernate.sample.Dinosaur other) {
      if (other == org.codeandmagic.protobuf2hibernate.sample.Dinosaur.getDefaultInstance()) return this;
      if (other.hasUuid()) {
        setUuid(other.getUuid());
      }
      if (other.hasAge()) {
        setAge(other.getAge());
      }
      if (other.hasName()) {
        setName(other.getName());
      }
      if (other.hasTailLength()) {
        setTailLength(other.getTailLength());
      }
      if (!other.victims_.isEmpty()) {
        if (result.victims_.isEmpty()) {
          result.victims_ = new java.util.ArrayList<java.lang.String>();
        }
        result.victims_.addAll(other.victims_);
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
          case 10: {
            setUuid(input.readString());
            break;
          }
          case 16: {
            setAge(input.readInt64());
            break;
          }
          case 26: {
            setName(input.readString());
            break;
          }
          case 37: {
            setTailLength(input.readFloat());
            break;
          }
          case 42: {
            addVictims(input.readString());
            break;
          }
        }
      }
    }
    
    
    // required string uuid = 1;
    public boolean hasUuid() {
      return result.hasUuid();
    }
    public java.lang.String getUuid() {
      return result.getUuid();
    }
    public Builder setUuid(java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  result.hasUuid = true;
      result.uuid_ = value;
      return this;
    }
    public Builder clearUuid() {
      result.hasUuid = false;
      result.uuid_ = getDefaultInstance().getUuid();
      return this;
    }
    
    // required int64 age = 2;
    public boolean hasAge() {
      return result.hasAge();
    }
    public long getAge() {
      return result.getAge();
    }
    public Builder setAge(long value) {
      result.hasAge = true;
      result.age_ = value;
      return this;
    }
    public Builder clearAge() {
      result.hasAge = false;
      result.age_ = 0L;
      return this;
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
    
    // optional float tailLength = 4;
    public boolean hasTailLength() {
      return result.hasTailLength();
    }
    public float getTailLength() {
      return result.getTailLength();
    }
    public Builder setTailLength(float value) {
      result.hasTailLength = true;
      result.tailLength_ = value;
      return this;
    }
    public Builder clearTailLength() {
      result.hasTailLength = false;
      result.tailLength_ = 0F;
      return this;
    }
    
    // repeated string victims = 5;
    public java.util.List<java.lang.String> getVictimsList() {
      return java.util.Collections.unmodifiableList(result.victims_);
    }
    public int getVictimsCount() {
      return result.getVictimsCount();
    }
    public java.lang.String getVictims(int index) {
      return result.getVictims(index);
    }
    public Builder setVictims(int index, java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  result.victims_.set(index, value);
      return this;
    }
    public Builder addVictims(java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  if (result.victims_.isEmpty()) {
        result.victims_ = new java.util.ArrayList<java.lang.String>();
      }
      result.victims_.add(value);
      return this;
    }
    public Builder addAllVictims(
        java.lang.Iterable<? extends java.lang.String> values) {
      if (result.victims_.isEmpty()) {
        result.victims_ = new java.util.ArrayList<java.lang.String>();
      }
      super.addAll(values, result.victims_);
      return this;
    }
    public Builder clearVictims() {
      result.victims_ = java.util.Collections.emptyList();
      return this;
    }
  }
  
  static {
    org.codeandmagic.protobuf2hibernate.sample.Descriptor_Dinosaur.getDescriptor();
  }
  
  static {
    org.codeandmagic.protobuf2hibernate.sample.Descriptor_Dinosaur.internalForceInit();
  }
}

