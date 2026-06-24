import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Entry } from "../entity/entry";
import { EntryCommentEntity } from '../entity/entry-comment-entity';
import { User } from "../entity/user";

@Injectable({ providedIn: 'root' })
export class EntryService {

    private readonly springBackendApi = 'http://localhost:8080/api/';

    backendClient: HttpClient;
  constructor(httpConnectionToBackend: HttpClient) {
    this.backendClient = httpConnectionToBackend;
  }

    users = [
        { id: 1, firstname: 'Julia', lastname: 'Rudij', email: 'julia@example.com' },
        { id: 2, firstname: 'Andreas', lastname: 'Schulz', email: 'andreas@example.com' },
        { id: 3, firstname: 'Lisa', lastname: 'Hans', email: 'lisa@example.com' },
    ];

    entries: Entry[] = [
        { id: 1, user: this.users[0], note: 'Die Sonne scheint heute stark.', time: new Date('2026-06-18T11:20:00'), comments: [] },
        { id: 2, user: this.users[1], note: 'Mein Schrank baut sich von selbst auf.', time: new Date('2026-06-18T09:45:00'), comments: [] },
        { id: 3, user: this.users[2], note: 'Ich habe ein gutes Business aufgebaut.', time: new Date('2026-06-17T18:10:00'), comments: [] },
    ];

    getEntries(): Entry[] {
        this.backendClient.get<Entry[]>(`${this.springBackendApi}/notes`).subscribe({
            next: (entries) => {
                this.entries = entries;
            },
            error: (error) => {
                console.error('Error fetching entries:', error);
                this.entries = [];
            },
        });
        return this.entries;
    }

    addEntry() {
        this.users.push({ id: this.users.length + 1, firstname: 'New', lastname: 'User', email: 'hello@gmail.com' });
        this.entries.push({
            id: this.entries.length + 1,
            user: this.users[this.users.length - 1],
            note: 'Hier könnte Ihre Werbung stehen.',
            time: new Date(),
            comments: [],
        });
        this.backendClient.post<Entry>(`${this.springBackendApi}/new`, this.entries[this.entries.length - 1]).subscribe({
            error: (error) => {
                console.error('Error creating entry:', error);
            },
        });
    }

    findEntryById(entryId: number): Entry | undefined {
        return this.entries.find((entry) => entry.id === entryId);
    }

    saveEntry(entry: Entry): void {
        this.backendClient.post<Entry>(`${this.springBackendApi}/edit`, entry.id);
    }

    deleteEntry(id: number) {
        this.backendClient.delete(`${this.springBackendApi}/delete/${id}`).subscribe({
            next: () => {
                console.log(`Entry with id ${id} deleted successfully.`);
                this.entries = this.entries.filter(entry => entry.id !== id);
            },
            error: (error) => {
                console.error(`Error deleting entry with id ${id}:`, error);
            }
        });
    }

    addCommentToEntry(entryId: number, entryComment: EntryCommentEntity): void {
        const entry = this.findEntryById(entryId);
        if (entry) {
            entry.comments.push(entryComment);
        } else {
            console.error(`Entry with id ${entryId} not found`);
        }
    }
}


