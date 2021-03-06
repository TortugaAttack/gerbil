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
package org.aksw.gerbil.dataset.impl.sw;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFLanguages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileBasedRDFDataset extends AbstractRDFDataset {


	private static final Logger LOGGER = LoggerFactory.getLogger(FileBasedRDFDataset.class);

    private String filePath;
    private Lang language;

    public FileBasedRDFDataset(String filePath, String name, Lang language) {
        super(name);
        this.filePath = filePath;
        this.language = language;
    }

    public FileBasedRDFDataset(String filePath, String name, String language) {
        super(name);
        this.filePath = filePath;
        this.language = RDFLanguages.nameToLang(language);
        if (this.language == null) {
            this.language = fileExtToLang(filePath);
        }
        if (this.language == null) {
            throw new IllegalArgumentException("Couldn't determine language of dataset.");
        }
    }

    public FileBasedRDFDataset(String filePath, String name) {
        super(name);
        this.filePath = filePath;
        this.language = fileExtToLang(filePath);
        if (this.language == null) {
            throw new IllegalArgumentException("Couldn't determine language of dataset.");
        }
    }

    public FileBasedRDFDataset(String filePath, Lang language) {
        super("");
        this.filePath = filePath;
        this.language = language;
    }

    public FileBasedRDFDataset(String filePath) {
        super("");
        this.filePath = filePath;
        this.language = fileExtToLang(filePath);
        if (this.language == null) {
            throw new IllegalArgumentException("Couldn't determine language of dataset.");
        }
    }

    @Override
    protected InputStream getDataAsInputStream() {
        FileInputStream fin = null;
        try {
            LOGGER.debug("Loading RDF dataset from {}", filePath);
            fin = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            LOGGER.error("Couldn't load RDF dataset from file.", e);
        }
        return fin;
    }

    @Override
    protected Lang getDataLanguage() {
        return language;
    }

    protected static Lang fileExtToLang(String filePath) {
        File file = new File(filePath);
        String ext = file.getName();
        int pos = ext.lastIndexOf('.');
        if (pos < 0) {
            return null;
        }
        return RDFLanguages.fileExtToLang(ext.substring(pos));
    }
}
