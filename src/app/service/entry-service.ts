import { Injectable } from '@angular/core';
import { Entry } from "../entity/entry";

@Injectable({ providedIn: 'root' })
export class EntryService {
    
    entries: Entry[] = [
        { id: 1, firstname: 'Julia', lastname: 'Musterfrau', email: 'julia@example.com', note: 'Die Sonne scheint heute stark.', time: new Date('2026-06-18T11:20:00') , comments: []},
        { id: 2, firstname: 'Andreas', lastname: 'Muster', email: 'andreas@example.com', note: 'Mein Schrank baut sich von selbst auf.', time: new Date('2026-06-18T09:45:00'), comments: [] },
        { id: 3, firstname: 'Lisa', lastname: 'Musterfrau', email: 'lisa@example.com', note: 'Ich habe ein gutes Business aufgebaut.', time: new Date('2026-06-17T18:10:00'), comments: [] },
    ].sort((a, b) => b.time.getTime() - a.time.getTime());

    addEntry() {
        this.entries.push({
            id: this.entries.length + 1,
            firstname: 'Neuer',
            lastname: 'Mensch',
            email: 'neuer@example.com',
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
        this.entries = this.entries.splice(1,1);
    }
}


