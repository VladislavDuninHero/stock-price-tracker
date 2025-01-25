import {FieldNameEnum} from "./FildNameEnum.js";
import {Constant} from "./Constant.js";


export class FieldFactory {

    fields = new Map([
        [FieldNameEnum.REGISTRATION_FORM_ERROR_CONTAINER, Constant.REGISTRATION_FORM_ERROR_CONTAINER]
    ]);

    getField(field) {
        if (!this.fields.has(field)) {
            throw new Error("Field is not implemented");
        }

        return this.fields.get(field);
    }
}