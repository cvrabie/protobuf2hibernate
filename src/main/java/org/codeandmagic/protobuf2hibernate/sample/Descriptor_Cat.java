// Generated by the protocol buffer compiler.  DO NOT EDIT!

package org.codeandmagic.protobuf2hibernate.sample;

public final class Descriptor_Cat {
  private Descriptor_Cat() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  static com.google.protobuf.Descriptors.Descriptor
    internal_static_domain_Cat_descriptor;
  static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_domain_Cat_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\tcat.proto\022\006domain\032\014person.proto\032\ttoy.p" +
      "roto\"\257\001\n\003Cat\022\014\n\004uuid\030\001 \002(\t\022\017\n\007created\030\002 " +
      "\002(\003\022\014\n\004name\030\003 \002(\t\022\022\n\nhairLength\030\004 \001(\002\022\017\n" +
      "\007kittens\030\005 \003(\t\022\035\n\005owner\030\006 \001(\0132\016.domain.P" +
      "erson\022\034\n\007friends\030\007 \003(\0132\013.domain.Cat\022\031\n\004t" +
      "oys\030\010 \003(\0132\013.domain.ToyB@\n*org.codeandmag" +
      "ic.protobuf2hibernate.sampleB\016Descriptor" +
      "_CatH\001P\001"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_domain_Cat_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_domain_Cat_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_domain_Cat_descriptor,
              new java.lang.String[] { "Uuid", "Created", "Name", "HairLength", "Kittens", "Owner", "Friends", "Toys", },
              org.codeandmagic.protobuf2hibernate.sample.Cat.class,
              org.codeandmagic.protobuf2hibernate.sample.Cat.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          org.codeandmagic.protobuf2hibernate.sample.Descriptor_Person.getDescriptor(),
          org.codeandmagic.protobuf2hibernate.sample.Descriptor_Toy.getDescriptor(),
        }, assigner);
  }
  
  public static void internalForceInit() {}
}
