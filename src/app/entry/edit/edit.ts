import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Entry } from '../../entity/entry';
import { EntryService } from '../../service/entry-service';

@Component({
    selector: 'edit',
    standalone: true,
    imports: [DatePipe, FormsModule],
    templateUrl: './edit.html',
    styleUrls: ['./edit.css'],
})
export class Edit {
    title = 'Eintrag bearbeiten';
    entryId = 0;
    entry: Entry | undefined;

    constructor(
        public router: Router,
        private route: ActivatedRoute,
        private service: EntryService,
    ) {
        this.entryId = Number(this.route.snapshot.paramMap.get('id'));
        this.entry = this.service.findEntryById(this.entryId);
    }

    navigateToOverview(): void {
        this.router.navigate(['/overview']);
    }

    saveEntry(): void {
        if (!this.entry) {
            return;
        }

        this.service.saveEntry(this.entry);
        this.navigateToOverview();
    }

    deleteEntry(): void {
        if (!this.entry) {
            return;
        }
        this.service.deleteEntry(this.entry.id);
        this.navigateToOverview();
    }
    commentEntry(): void {
        if (!this.entry) {
            return;
        }
        this.router.navigate(['/comment', this.entry.id], );
    }
}
