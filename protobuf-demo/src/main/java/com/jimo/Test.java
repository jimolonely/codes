package com.jimo;

import com.google.protobuf.InvalidProtocolBufferException;
import com.jimo.model.PersonProto;

public class Test {
    public static void main(String[] args) {
        final PersonProto.Person.Builder builder = PersonProto.Person.newBuilder();
        builder.setAge(18);
        builder.setName("jimo");
        final PersonProto.Person person = builder.build();

        final byte[] bytes = person.toByteArray();
        System.out.println("bytes.length=" + bytes.length);

        try {
            final PersonProto.Person p2 = PersonProto.Person.parseFrom(bytes);
            System.out.println(p2);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

    }
}
