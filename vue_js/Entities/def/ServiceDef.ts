import { type IService } from '../interface/IService';
import { type IPnvColumn } from 'vue-pnv-components-library';

export const DefaultService: IService = {
  id: '',
  name: '',
  description: '',
  version: '',
  status: '',
  usedServices: [],
  serviceUsers: [],
};

export const serviceFields = {
  id: {
    name: 'id',
    label: 'ID',
    isKey: true,
    type: 'string',
    rules: [(val: string) => !!val || 'ID is required'],
  } as IPnvColumn,
  name: {
    name: 'name',
    label: 'Name',
    type: 'string',
    rules: [(val: string) => !!val || 'Name is required'],
  } as IPnvColumn,
  description: {
    name: 'description',
    label: 'Description',
    type: 'string',
  } as IPnvColumn,
  version: {
    name: 'version',
    label: 'Version',
    type: 'string',
  } as IPnvColumn,
  status: {
    name: 'status',
    label: 'Status',
    type: 'string',
  } as IPnvColumn,
  // usedServices and serviceUsers might be handled differently in the UI (e.g., dedicated views or lists)
  // and not directly as simple grid columns.
};

export function useServiceFields() {
  return serviceFields;
}
