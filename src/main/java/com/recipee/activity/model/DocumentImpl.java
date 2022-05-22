package com.recipee.activity.model;

import com.recipee.utils.Utils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
public class DocumentImpl {
    @Id
    public String id;
    private long cts = Utils.getTime();
    private long uts = Utils.getTime();

}
