import { User } from "./user";

export interface EntryCommentEntity {
    id: number,
    user: User,
    note: string,
    time: Date,
}