package net.royalmind.library.utilities.json;

import java.util.*;

public class XMLTokener extends JSONTokener
{
    public static final HashMap entity;

    public XMLTokener(final String s) {
        super(s);
    }

    public String nextCDATA() throws JSONException {
        final StringBuffer sb = new StringBuffer();
        int length;
        do {
            final char next = this.next();
            if (this.end()) {
                throw this.syntaxError("Unclosed CDATA");
            }
            sb.append(next);
            length = sb.length() - 3;
        } while (length < 0 || sb.charAt(length) != ']' || sb.charAt(length + 1) != ']' || sb.charAt(length + 2) != '>');
        sb.setLength(length);
        return sb.toString();
    }

    public Object nextContent() throws JSONException {
        char c;
        do {
            c = this.next();
        } while (Character.isWhitespace(c));
        if (c == '\0') {
            return null;
        }
        if (c == '<') {
            return XML.LT;
        }
        final StringBuffer sb = new StringBuffer();
        while (c != '<' && c != '\0') {
            if (c == '&') {
                sb.append(this.nextEntity(c));
            }
            else {
                sb.append(c);
            }
            c = this.next();
        }
        this.back();
        return sb.toString().trim();
    }

    public Object nextEntity(final char c) throws JSONException {
        final StringBuffer sb = new StringBuffer();
        char next;
        while (true) {
            next = this.next();
            if (!Character.isLetterOrDigit(next) && next != '#') {
                break;
            }
            sb.append(Character.toLowerCase(next));
        }
        if (next == ';') {
            final String string = sb.toString();
            final Object value = XMLTokener.entity.get(string);
            return (value != null) ? value : (c + string + ";");
        }
        throw this.syntaxError("Missing ';' in XML entity: &" + (Object)sb);
    }

    public Object nextMeta() throws JSONException {
        char next;
        do {
            next = this.next();
        } while (Character.isWhitespace(next));
        switch (next) {
            case '\0': {
                throw this.syntaxError("Misshaped meta tag");
            }
            case '<': {
                return XML.LT;
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"':
            case '\'': {
                char next2;
                do {
                    next2 = this.next();
                    if (next2 == '\0') {
                        throw this.syntaxError("Unterminated string");
                    }
                } while (next2 != next);
                return Boolean.TRUE;
            }
            default: {
                while (true) {
                    final char next3 = this.next();
                    if (Character.isWhitespace(next3)) {
                        return Boolean.TRUE;
                    }
                    switch (next3) {
                        case '\0':
                        case '!':
                        case '\"':
                        case '\'':
                        case '/':
                        case '<':
                        case '=':
                        case '>':
                        case '?': {
                            this.back();
                            return Boolean.TRUE;
                        }
                        default: {
                            continue;
                        }
                    }
                }
                //break;
            }
        }
    }

    public Object nextToken() throws JSONException {
        char c;
        do {
            c = this.next();
        } while (Character.isWhitespace(c));
        switch (c) {
            case '\0': {
                throw this.syntaxError("Misshaped element");
            }
            case '<': {
                throw this.syntaxError("Misplaced '<'");
            }
            case '>': {
                return XML.GT;
            }
            case '/': {
                return XML.SLASH;
            }
            case '=': {
                return XML.EQ;
            }
            case '!': {
                return XML.BANG;
            }
            case '?': {
                return XML.QUEST;
            }
            case '\"':
            case '\'': {
                final char c2 = c;
                final StringBuffer sb = new StringBuffer();
                while (true) {
                    final char next = this.next();
                    if (next == '\0') {
                        throw this.syntaxError("Unterminated string");
                    }
                    if (next == c2) {
                        return sb.toString();
                    }
                    if (next == '&') {
                        sb.append(this.nextEntity(next));
                    }
                    else {
                        sb.append(next);
                    }
                }
                //break;
            }
            default: {
                final StringBuffer sb2 = new StringBuffer();
                while (true) {
                    sb2.append(c);
                    c = this.next();
                    if (Character.isWhitespace(c)) {
                        return sb2.toString();
                    }
                    switch (c) {
                        case '\0': {
                            return sb2.toString();
                        }
                        case '!':
                        case '/':
                        case '=':
                        case '>':
                        case '?':
                        case '[':
                        case ']': {
                            this.back();
                            return sb2.toString();
                        }
                        case '\"':
                        case '\'':
                        case '<': {
                            throw this.syntaxError("Bad character in a name");
                        }
                        default: {
                            continue;
                        }
                    }
                }
                //break;
            }
        }
    }

    public boolean skipPast(final String s) throws JSONException {
        int n = 0;
        final int length = s.length();
        final char[] array = new char[length];
        for (int i = 0; i < length; ++i) {
            final char next = this.next();
            if (next == '\0') {
                return false;
            }
            array[i] = next;
        }
        while (true) {
            int n2 = n;
            boolean b = true;
            for (int j = 0; j < length; ++j) {
                if (array[n2] != s.charAt(j)) {
                    b = false;
                    break;
                }
                if (++n2 >= length) {
                    n2 -= length;
                }
            }
            if (b) {
                return true;
            }
            final char next2 = this.next();
            if (next2 == '\0') {
                return false;
            }
            array[n] = next2;
            if (++n < length) {
                continue;
            }
            n -= length;
        }
    }

    static {
        (entity = new HashMap(8)).put("amp", XML.AMP);
        XMLTokener.entity.put("apos", XML.APOS);
        XMLTokener.entity.put("gt", XML.GT);
        XMLTokener.entity.put("lt", XML.LT);
        XMLTokener.entity.put("quot", XML.QUOT);
    }
}
