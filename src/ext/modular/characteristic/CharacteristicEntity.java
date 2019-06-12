package ext.modular.characteristic;

import ext.modular.common.BasicEntity;
import ext.modular.procedure.ProcedureEntity;

/**
 * des:
 *
 * @author fxiao
 * @date 2019/6/4 14:22
 */
public class CharacteristicEntity extends BasicEntity {
    private ProcedureEntity procedureEntity;
    private String name;
    private int total;
    private int coefficient;
    private int ppm_order;

    public ProcedureEntity getProcedureEntity() {
        return procedureEntity;
    }

    public void setProcedureEntity(ProcedureEntity procedureEntity) {
        this.procedureEntity = procedureEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getPpm_order() {
        return ppm_order;
    }

    public void setPpm_order(int ppm_order) {
        this.ppm_order = ppm_order;
    }
}

