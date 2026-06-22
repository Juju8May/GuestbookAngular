import { Injectable } from '@angular/core';
import { Entry } from "../entity/entry";
import { EntryCommentEntity } from '../entity/entry-comment-entity';
import { User } from "../entity/user";

@Injectable({ providedIn: 'root' })
export class EntryService {

    users = [
        { id: 1, firstname: 'Julia', lastname: 'Rudij', email: 'julia@example.com' },
        { id: 2, firstname: 'Andreas', lastname: 'Schulz', email: 'andreas@example.com' },
        { id: 3, firstname: 'Lisa', lastname: 'Hans', email: 'lisa@example.com' },
    ];

    entries: Entry[] = [
        { id: 1, user: this.users[0], note: 'Die Sonne scheint heute stark.', time: new Date('2026-06-18T11:20:00'), comments: [] },
        { id: 2, user: this.users[1], note: 'Mein Schrank baut sich von selbst auf.', time: new Date('2026-06-18T09:45:00'), comments: [] },
        { id: 3, user: this.users[2], note: 'Ich habe ein gutes Business aufgebaut.', time: new Date('2026-06-17T18:10:00'), comments: [] },
    ].sort((a, b) => b.time.getTime() - a.time.getTime());

    addEntry() {
        this.entries.push({
            id: this.entries.length + 1,
            user: this.users[Math.floor(Math.random() * this.users.length)] as User,
            note: 'Hier könnte Ihre Werbung stehen.',
            time: new Date(),
            comments: [],
        })
    }
    findEntryById(entryId: number): Entry | undefined {
        return this.entries.find(entry => entry.id === entryId);
    }
    saveEntry(entry: Entry): void {
        const index = this.entries.findIndex((existingEntry) => existingEntry.id === entry.id);

        if (index !== -1) {
            this.entries[index] = { ...entry };
        }
    }
    deleteEntry(id: number) {
        this.entries = this.entries.splice(1, 1);
    }
    addCommentToEntry(entryId: number, entryComment: EntryCommentEntity): void {
        const entry = this.findEntryById(entryId);
        if (entry) {
            entry.comments.push(entryComment);
            this.saveEntry(entry);
        }
    }
}


