import { DatePipe } from '@angular/common';import { Component, inject } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Entry } from '../../entity/entry';
import { EntryService } from '../../service/entry-service';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
    selector: 'edit',
    standalone: true,
    imports: [DatePipe, ReactiveFormsModule],
    templateUrl: './edit.html',
    styleUrls: ['./edit.css'],
})
export class Edit {
    title = 'Eintrag bearbeiten';

    isSubmitted = false;
    entryForm = new FormGroup({
        note: new FormControl('', [Validators.required, Validators.minLength(10), Validators.maxLength(500)]),
    });
    private service = inject(EntryService);
    private route = inject(ActivatedRoute);
    private router = inject(Router);
    private entryId = Number(this.route.snapshot.paramMap.get('id'));
    private entry = this.service.findEntryById(this.entryId);
    
    get noteControl(): FormControl<string | null> {
        return this.entryForm.get('note') as FormControl<string | null>;
    }

    navigateToOverview(): void {
        this.router.navigate(['/overview']);
    }



    saveEntry(): void {
        this.isSubmitted = true;
        if (!this.entry || this.entryForm.invalid) {
            this.entryForm.markAllAsTouched();
            return;
        }

        this.entry.note = this.noteControl.value?.trim() ?? '';
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
        this.router.navigate([`/entry/${this.entryId}/comment`]);
    }
}
