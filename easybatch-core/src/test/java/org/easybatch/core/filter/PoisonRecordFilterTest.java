/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easybatch.core.filter;

import org.easybatch.core.api.Header;
import org.easybatch.core.record.PoisonRecord;
import org.easybatch.core.record.StringRecord;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link PoisonRecordFilter}.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class PoisonRecordFilterTest {

    private PoisonRecordFilter poisonRecordFilter;

    @Before
    public void setUp() throws Exception {
        poisonRecordFilter = new PoisonRecordFilter();
    }

    @Test
    public void whenTheRecordIsOfTypePoisonRecord_ThenItShouldBeFiltered() {
        assertThat(poisonRecordFilter.filterRecord(new PoisonRecord())).isTrue();
        assertThat(poisonRecordFilter.filterRecord(new CustomPoisonRecord())).isTrue();
    }

    @Test
    public void whenTheRecordIsNotOfTypePoisonRecord_ThenItNotShouldBeFiltered() {
        assertThat(poisonRecordFilter.filterRecord(new StringRecord(new Header(1l, "DataSource", new Date()), "foo"))).isFalse();
    }

    class CustomPoisonRecord extends PoisonRecord {
        public CustomPoisonRecord() {
        }
    }

}
