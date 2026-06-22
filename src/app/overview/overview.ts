import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Entry } from "../entity/entry";
import { EntryService } from "../service/entry-service";

@Component({
    selector: 'overview',
    standalone: true,
    imports: [DatePipe, RouterOutlet],
    templateUrl: './overview.html',
    styleUrls: ['./overview.css'],
})
export class Overview {
    title = 'Überblick';

    constructor(public router: Router, public service: EntryService) {
        this.router = router;
        this.service = service;
    }
    editEntry(entryId: number): void {
        this.router.navigate(['/edit', entryId]);
    }
    navigateToHome(): void {
        this.router.navigate(['/home']);
    }
    getEntries(): Entry[] {
        return this.service.entries;
    }
    addEntry(): void {
        this.service.addEntry();
    }
}