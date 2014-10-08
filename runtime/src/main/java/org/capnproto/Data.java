package org.capnproto;

import java.nio.ByteBuffer;

public final class Data {
    public static final class Factory implements FromPointerReaderBlobDefault<Reader>,
                                      FromPointerBuilderBlobDefault<Builder>,
                                      InitSizedFromPointerBuilder<Builder> {
        public Reader fromPointerReaderBlobDefault(SegmentReader segment, int pointer, java.nio.ByteBuffer defaultBuffer,
                                                   int defaultOffset, int defaultSize) {
            return WireHelpers.readDataPointer(segment, pointer, defaultBuffer, defaultOffset, defaultSize);
        }
        public Builder fromPointerBuilderBlobDefault(SegmentBuilder segment, int pointer,
                                                     java.nio.ByteBuffer defaultBuffer, int defaultOffset, int defaultSize) {
            return WireHelpers.getWritableDataPointer(pointer,
                                                      segment,
                                                      defaultBuffer,
                                                      defaultOffset,
                                                      defaultSize);
        }

        public Builder initSizedFromPointerBuilder(SegmentBuilder segment, int pointer, int size) {
            return WireHelpers.initDataPointer(pointer, segment, size);
        }
    }
    public static final Factory factory = new Factory();

    public static final class Reader {
        public final ByteBuffer buffer;
        public final int offset; // in bytes
        public final int size; // in bytes

        public Reader(ByteBuffer buffer, int offset, int size) {
            this.buffer = buffer;
            this.offset = offset * 8;
            this.size = size;
        }

        public Reader(byte[] bytes) {
            this.buffer = ByteBuffer.wrap(bytes);
            this.offset = 0;
            this.size = bytes.length;
        }

        public final int size() {
            return this.size;
        }

        public ByteBuffer asByteBuffer() {
            ByteBuffer dup = this.buffer.duplicate();
            dup.position(this.offset);
            ByteBuffer result = dup.slice();
            result.limit(this.size);
            return result;
        }

        public byte[] toArray() {
            ByteBuffer dup = this.buffer.duplicate();
            byte result[] = new byte[this.size];
            dup.position(this.offset);
            dup.get(result, 0, this.size);
            return result;
        }
    }

    public static final class Builder {
        public final ByteBuffer buffer;
        public final int offset; // in bytes
        public final int size; // in bytes

        public Builder(ByteBuffer buffer, int offset, int size) {
            this.buffer = buffer;
            this.offset = offset;
            this.size = size;
        }

        public byte[] toArray() {
            ByteBuffer dup = this.buffer.duplicate();
            byte result[] = new byte[this.size];
            dup.position(this.offset);
            dup.get(result, 0, this.size);
            return result;
        }
    }
}
