**BetterHM-App-Backend**
=========================

Dieser Backend-Dienst dient als Schnittstelle für die inoffizielle BetterHM-App, um eine Reihe von Informationen und Funktionen bereitzustellen.

**Endpunkte**
---------------

### /v1/calendar

* **Beschreibung**: Diese Methode liefert eine Liste von Ressourcen, die zur Verfügung stehen.
* **HTTP-Methode**: GET
* **Antwortformat**: JSON
* **Beispielantwort**:
```json
[
  {
    "id": "{UUID}",
    "name": "{NAME}",
    "url": "{URL}",
    "sourceUrl": "{SOURCE_URL}",
    "description": "{DESCRIPTION}"
  }
]
```
* Hinweis: Die iCal-Dateien der Kalender werden durch die Anwendung gecached und können unter /static/calendar/{UUID}.ics erreicht werden.


### /v1/calendar/{enum_name}

* **Beschreibung**: Diese Methode liefert Informationen über ein bestimmtes Element einer Ressource.
* **HTTP-Methode**: GET
* **Antwortformat**: JSON
* **Beispielantwort**:
```json
{
  "id": "{UUID}",
  "name": "{NAME}",
  "url": "{URL}",
  "sourceUrl": "{SOURCE_URL}",
  "description": "{DESCRIPTION}"
}
```


### /v1/capacity

* **Beschreibung**: Diese Methode liefert eine Liste von Statusinformationen für verschiedene Ressourcen.
* **HTTP-Methode**: GET
* **Antwortformat**: JSON
* **Beispielantwort**:
```json
[
  {
    "enum_name": "{ENUM_NAME}",
    "current": {int},
    "percent": {float},
    "updated": "{TIMESTAMP}"
  }
]
```

### /v1/capacity/{enum_name}

* **Beschreibung**: Diese Methode liefert Informationen über den Status eines bestimmten Elements einer Ressource.
* **HTTP-Methode**: GET
* **Antwortformat**: JSON
* **Beispielantwort**:
```json
{
  "enum_name": "{ENUM_NAME}",
  "current": {int},
  "percent": {float},
  "updated": "{UPDATED}"
}
```

### /v1/movies

* **Beschreibung**: Gibt das aktuelle Kinoprogramm zurück
* **HTTP-Methode**: GET
* **Antwortformat**: JSON

**Technische Details**
-------------------------

* Die Endpunkte werden über HTTP bereitgestellt.
* Die Antwortformate sind JSON-Objekte.
* Die Anwendung wird durch eine Konfigurationsdatei config.yml im Ordner /resources/yaml konfiguriert

**Fehlerbehandlung**
----------------------

* Wenn eine Anfrage erfolgreich ist, wird ein Statuscode 200 zurückgegeben.
* Wenn eine Anfrage nicht erfolgreich ist, wird ein Statuscode 404 zurückgegeben, wenn die angeforderte Ressource nicht gefunden werden kann.

**API-Verwendung**
-------------------

* Die API kann über HTTP-Methoden getestet werden.
* Die API-Endpunkte sollten korrekt parametriert werden, um korrekte Ergebnisse zu erhalten.
