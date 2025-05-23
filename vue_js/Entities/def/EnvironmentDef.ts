import { type IEnvironment } from '../interface/IEnvironment';
import { type IPnvColumn } from 'vue-pnv-components-library';

export const DefaultEnvironment: IEnvironment = {
  id: '',
  name: '',
  description: '',
  services: [],
};

export const environmentFields = {
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
  // Add other fields as necessary, e.g., for services if displaying them in the grid
};

export function useEnvironmentFields() {
  return environmentFields;
}
