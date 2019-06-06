package ext.modular.procedure;

import ext.modular.characteristic.CharacteristicEntity;
import ext.modular.common.BasicEntity;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/4 14:25
 */
public class ProcedureEntity extends BasicEntity {
    private String name;
    private CharacteristicEntity characteristic;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CharacteristicEntity getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(CharacteristicEntity characteristic) {
        this.characteristic = characteristic;
    }
}
