import { type IClient } from '../interface/IClient';
import { type IPnvColumn } from 'vue-pnv-components-library';

export const DefaultClient: IClient = {
  id: '',
  name: '',
  description: '',
  environments: [],
};

export const clientFields = {
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
  // Add other fields as necessary
};

export function useClientFields() {
  return clientFields;
}
