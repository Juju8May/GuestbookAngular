import { User } from './user';

export interface Entry {
    id: number;
    user: User;
    note: string;
    time: Date;
    comments: Comment[];
}
