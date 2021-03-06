/**
 * This file is part of General Entity Annotator Benchmark.
 *
 * General Entity Annotator Benchmark is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * General Entity Annotator Benchmark is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with General Entity Annotator Benchmark.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.aksw.gerbil.tools;

import org.aksw.gerbil.dataset.impl.sw.FileBasedRDFDataset;
import org.aksw.gerbil.exceptions.GerbilException;
import org.apache.commons.io.IOUtils;
import org.apache.jena.riot.Lang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NIFDatasetLoadingTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(NIFDatasetLoadingTest.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            LOGGER.error(
                    "Wrong usage. Need exactly one single argument. Correct usage: 'NIFDatasetLoadingTest <path-to-nif-file>'.");
            return;
        }
        LOGGER.info("Starting loading of given test dataset \"" + args[0] + "\"...");
        FileBasedRDFDataset dataset = new FileBasedRDFDataset(args[0], "Test dataset", Lang.TTL);
        try {
            dataset.init();
        } catch (GerbilException e) {
            LOGGER.error("Got an exception while trying to load the dataset.", e);
        }
        IOUtils.closeQuietly(dataset);
        LOGGER.info("Finished loading of given test dataset.");
    }
}
