
package Status;

import java.util.HashMap;
import java.util.Map;

public class StatusRegistry {
    private static final Map<String, Class<? extends Status>> statusMap = new HashMap<>();

    static {
        statusMap.put("BleedStatus", BleedStatus.class);
        statusMap.put("BlindStatus", BlindStatus.class);
        statusMap.put("DazeStatus", DazeStatus.class);
        statusMap.put("DrainStatus", DrainStatus.class);
        statusMap.put("EchoOfEternity", EchoOfEternityAuraStatus.class);
        statusMap.put("EtherealChainsStatus", EtherealChainsStatus.class);
        statusMap.put("FearStatus", FearStatus.class);
        statusMap.put("FireStatus", FireStatus.class);
        statusMap.put("IceStatus", IceStatus.class);
        statusMap.put("IllusoryDoubleStatus", IllusoryDoubleStatus.class);
        statusMap.put("LifeStealStatus", LifeStealStatus.class);
        statusMap.put("MindProbeStatus", MindProbeStatus.class);
        statusMap.put("PoisonStatus", PoisonStatus.class);
        statusMap.put("RadiantStatus", RadiantStatus.class);
        statusMap.put("ReduceDefenseStatus", ReduceDefenseStatus.class);
        statusMap.put("ReduceStrengthStatus", ReduceStrengthStatus.class);
    }

    public static Class<? extends Status> getStatusClass(String name) {
        return statusMap.get(name);
    }
}
