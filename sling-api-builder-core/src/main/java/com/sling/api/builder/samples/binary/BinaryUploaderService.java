package com.sling.api.builder.samples.binary;

import com.sling.api.builder.samples.binary.impl.BinaryFile;
import com.sling.api.builder.samples.binary.impl.Type;
import org.apache.sling.api.resource.ResourceResolver;

import java.util.List;
import java.util.Map;

public interface BinaryUploaderService {

    void updateRepositoryBinariesAndClose(ResourceResolver resourceResolver, String userID, Map<Type, List<BinaryFile>> files);

}
