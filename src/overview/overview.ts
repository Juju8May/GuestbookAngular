import { Entry } from "./entry";
import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
    selector: 'app-overview',
    standalone: true,
    imports: [DatePipe],
    templateUrl: './overview.html',
    styleUrls: ['./overview.css'],
})
export class Overview {

    title = 'Gästebuch';
constructor(private router: Router) {
    this.router = router;
}
    entries: Entry[] = [
        { id: 1, name: 'Julia', note: 'Die Sonne scheint heute stark.', time: new Date('2026-06-18T11:20:00') },
        { id: 2, name: 'Andreas', note: 'Mein Schrank baut sich von selbst auf.', time: new Date('2026-06-18T09:45:00') },
        { id: 3, name: 'Lisa', note: 'Ich habe ein gutes Business aufgebaut.', time: new Date('2026-06-17T18:10:00') },
    ].sort((a, b) => b.time.getTime() - a.time.getTime());

    addEntry() {
        this.entries.push({
            id: this.entries.length + 1,
            name: 'Neuer Mensch',
            note: 'Hier könnte Ihre Werbung stehen.',
            time: new Date(),
        })
    }
}