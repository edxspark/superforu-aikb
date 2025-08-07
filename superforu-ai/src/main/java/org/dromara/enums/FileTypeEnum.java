package org.dromara.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FileTypeEnum {

    PDF("pdf", "pdf"),
    MARKDOWN("markdown", "markdown");

    private final String key;
    private final String value;
}
