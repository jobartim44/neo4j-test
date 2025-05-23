import {
  EPnvFieldType,
  type IPnvEntityDef,
  type IPnvEntityId,
  type IPnvField,
} from 'vue-pnv-components-library';

/**
 * Liste des champs d'une phase
 */
interface IDomainFields {
  id: IPnvField;
  label: IPnvField;
}

/**
 * Singleton liste des champs
 */
let _domainFields: IDomainFields;
/**
 * Singleton entitydef
 */
let _domainDef: IPnvEntityDef;

/**
 * Liste des constantes de l'entité solr
 */
export function useDomainFields(): IDomainFields {
  if (!_domainFields) {
    _domainFields = {
      id: {
        fieldName: 'id',
        entityName: 'Domain',
        label: 'identifiant du domaine',
        type: EPnvFieldType.INTEGER
        chapter: 'identification',
        required: true,
        identification: true,
      } as IPnvField,
      label: {
        fieldName: 'label',
        entityName: 'Domain',
        label: 'Libellé du domaine',
        chapter: 'identification',
        type: EPnvFieldType.CHARACTER,,
      } as IPnvField,
    };
  }

  return _domainFields;
}

/**
 * Interface pour l'id
 */
export interface IDomainId extends IPnvEntityId {
  name: string;
}

/**
 * Définition de l'entité Domain
 */
export function useDomainDef(): IPnvEntityDef {
  if (!_domainDef) {
    _domainDef = {
      entityName: 'Domain',
      label: 'Domain',
      endPoint: 'domain',
      tabPnvFieldsId: [useDomainFields().id],
      pnvFields: Object.values(useDomainFields()),
    };
  }

  return _domainDef;
}
