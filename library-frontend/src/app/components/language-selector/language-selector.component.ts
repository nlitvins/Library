import {Component, OnInit} from '@angular/core';
import {Language, LanguageService} from '../../services/language.service';

@Component({
  selector: 'app-language-selector',
  templateUrl: './language-selector.component.html',
  styleUrls: ['./language-selector.component.scss']
})
export class LanguageSelectorComponent implements OnInit {
  selectedLanguage: Language = 'lv';

  constructor(private languageService: LanguageService) {
  }

  ngOnInit() {
    this.selectedLanguage = this.languageService.getCurrentLanguage();
  }

  onLanguageChange() {
    this.languageService.setLanguage(this.selectedLanguage);
  }
}
