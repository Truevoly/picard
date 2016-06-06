/*
 * The MIT License
 *
 * Copyright (c) 2009 The Broad Institute
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package picard.sam.markduplicates.util;

import htsjdk.samtools.util.SortingCollection;
import picard.PicardException;
import picard.sam.util.ReadNameInterface;

import java.io.*;

/** Codec for a string. */
public class RepresentativeReadCodec implements SortingCollection.Codec<ReadNameInterface> {
    protected DataInputStream in;
    protected DataOutputStream out;


    public SortingCollection.Codec<ReadNameInterface> clone() {
        return new RepresentativeReadCodec();
    }

    public void setOutputStream(final OutputStream os) { this.out = new DataOutputStream(os); }

    public void setInputStream(final InputStream is) { this.in = new DataInputStream(is); }

    public DataInputStream getInputStream() {
        return in;
    }

    public DataOutputStream getOutputStream() {
        return out;
    }

    public void encode(final ReadNameInterface rni) {
        try {
            this.out.writeInt(rni.read1IndexInFile);
            this.out.writeInt(rni.setSize);
            this.out.writeBytes(rni.readname);
        } catch (final IOException ioe) {
            throw new PicardException("Exception writing ReadEnds to file.", ioe);
        }
    }

    public ReadNameInterface decode() {
        final ReadNameInterface rni = new ReadNameInterface();
        try {
            rni.read1IndexInFile = this.in.readInt();
            rni.setSize = this.in.readInt();
            rni.readname = this.in.readUTF();
            return rni;
        } catch (final IOException ioe) {
            throw new PicardException("Exception writing ReadEnds to file.", ioe);
        }
    }
}
