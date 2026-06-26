import { DatePipe } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { Entry } from "../entity/entry";
import { EntryService } from "../service/entry-service";
import { Signal } from "@angular/core";

@Component({
    selector: 'overview',
    standalone: true,
    imports: [DatePipe],
    templateUrl: './overview.html',
    styleUrls: ['./overview.css'],
})

export class Overview {
    title = 'Überblick';

    private router = inject(Router);
    private service = inject(EntryService);

    editEntry(entryId: number): void {
        this.router.navigate([`/entry/${entryId}/edit`]);
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
    getCommentsCount(entry: Entry): number {
        return entry ? entry.comments.length : 0;
    }
}