/*
 * Decompiled with CFR 0_132.
 */
package gnu.trove.iterator;

import gnu.trove.iterator.TAdvancingIterator;

public interface TShortCharIterator
extends TAdvancingIterator {
    public short key();

    public char value();

    public char setValue(char var1);
}
