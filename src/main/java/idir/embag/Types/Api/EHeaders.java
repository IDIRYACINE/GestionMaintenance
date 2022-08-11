package idir.embag.Types.Api;

import java.util.Map;
import static java.util.Map.entry;

public abstract class EHeaders {
    private static Map<Headers,String> headersMap = Map.ofEntries(
        entry(Headers.access_token,"Access-Token"),
        entry(Headers.content_type,"Content-Type")
    );
    
    public static enum Headers {
        access_token,
        content_type,
    }

    public static String valueOf(Headers header) {
        return headersMap.get(header);
    }
}
